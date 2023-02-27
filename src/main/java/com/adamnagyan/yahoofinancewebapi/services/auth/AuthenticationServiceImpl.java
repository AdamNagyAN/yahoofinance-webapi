package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.ConfirmationTokenExpiredException;
import com.adamnagyan.yahoofinancewebapi.exceptions.ConfirmationTokenNotFoundException;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserIsAlreadyEnabledException;
import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.UserRepository;
import com.adamnagyan.yahoofinancewebapi.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationRequestMapper authenticationMapper;

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final ConfirmationTokenService confirmationTokenService;

	private final EmailService emailService;

	private final JwtService jwtService;

	@Value("${external-apis.confirm-email}")
	private String confirmUrl;

	@Value("${auth.confirmation-token-expiration-minutes}")
	private Integer confirmationTokenExpiry = 15;

	@Override
	public void register(RegisterRequestDto request) throws UserAlreadyExistAuthenticationException {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new UserAlreadyExistAuthenticationException();
		}
		User user = User.builder()
			.firstName(request.getFirstname())
			.lastName(request.getLastname())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.USER)
			.build();
		userRepository.save(user);
		generateConfirmationEmail(user);
	}

	@Override
	@SneakyThrows
	public void sendConfirmationEmail(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		if (user.getEnabled()) {
			throw new UserIsAlreadyEnabledException();
		}
		generateConfirmationEmail(user);
	}

	@SneakyThrows
	private void generateConfirmationEmail(User user) {
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = ConfirmationToken.builder()
			.token(token)
			.createdAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusMinutes(confirmationTokenExpiry))
			.user(user)
			.build();
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		String confirmationUrlWithParams = UriComponentsBuilder.fromHttpUrl(confirmUrl)
			.queryParam("token", token)
			.toUriString();
		emailService.send(user.getEmail(), buildEmail(user, confirmationUrlWithParams));
	}

	@Override
	public AuthenticationResponseDto login(AuthenticationRequestDto request) {
		authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		HashMap<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("firstname", user.getFirstName());
		extraClaims.put("lastname", user.getLastName());
		String jwtToken = jwtService.generateToken(extraClaims, user);
		return authenticationMapper.authenticationResponseToDTO(jwtToken);
	}

	@Override
	@SneakyThrows
	public void confirmRegistrationToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
			.orElseThrow(ConfirmationTokenNotFoundException::new);

		if (confirmationToken.getConfirmedAt() != null) {
			throw new UserIsAlreadyEnabledException();
		}

		if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new ConfirmationTokenExpiredException();
		}

		confirmationToken.setConfirmedAt(LocalDateTime.now());
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		User user = userRepository.findByEmail(confirmationToken.getUser().getEmail())
			.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		user.setEnabled(true);
		userRepository.save(user);
	}

	private String buildEmail(User user, String link) {
		return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n"
				+ "<head>\n" + "<!--[if gte mso 9]>\n" + "<xml>\n" + "  <o:OfficeDocumentSettings>\n"
				+ "    <o:AllowPNG/>\n" + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
				+ "  </o:OfficeDocumentSettings>\n" + "</xml>\n" + "<![endif]-->\n"
				+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "  <meta name=\"x-apple-disable-message-reformatting\">\n"
				+ "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n"
				+ "  <title></title>\n" + "  \n" + "    <style type=\"text/css\">\n"
				+ "      @media only screen and (min-width: 520px) {\n" + "  .u-row {\n"
				+ "    width: 500px !important;\n" + "  }\n" + "  .u-row .u-col {\n" + "    vertical-align: top;\n"
				+ "  }\n" + "\n" + "  .u-row .u-col-100 {\n" + "    width: 500px !important;\n" + "  }\n" + "\n" + "}\n"
				+ "\n" + "@media (max-width: 520px) {\n" + "  .u-row-container {\n"
				+ "    max-width: 100% !important;\n" + "    padding-left: 0px !important;\n"
				+ "    padding-right: 0px !important;\n" + "  }\n" + "  .u-row .u-col {\n"
				+ "    min-width: 320px !important;\n" + "    max-width: 100% !important;\n"
				+ "    display: block !important;\n" + "  }\n" + "  .u-row {\n" + "    width: 100% !important;\n"
				+ "  }\n" + "  .u-col {\n" + "    width: 100% !important;\n" + "  }\n" + "  .u-col > div {\n"
				+ "    margin: 0 auto;\n" + "  }\n" + "}\n" + "body {\n" + "  margin: 0;\n" + "  padding: 0;\n" + "}\n"
				+ "\n" + "table,\n" + "tr,\n" + "td {\n" + "  vertical-align: top;\n" + "  border-collapse: collapse;\n"
				+ "}\n" + "\n" + "p {\n" + "  margin: 0;\n" + "}\n" + "\n" + ".ie-container table,\n"
				+ ".mso-container table {\n" + "  table-layout: fixed;\n" + "}\n" + "\n" + "* {\n"
				+ "  line-height: inherit;\n" + "}\n" + "\n" + "a[x-apple-data-detectors='true'] {\n"
				+ "  color: inherit !important;\n" + "  text-decoration: none !important;\n" + "}\n" + "\n"
				+ "table, td { color: #000000; } #u_body a { color: #4a007e; text-decoration: underline; }\n"
				+ "    </style>\n" + "  \n" + "  \n" + "\n"
				+ "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n"
				+ "\n" + "</head>\n" + "\n"
				+ "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f6f6f6;color: #000000\">\n"
				+ "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n"
				+ "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n"
				+ "  <table id=\"u_body\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f6f6f6;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "  <tbody>\n" + "  <tr style=\"vertical-align: top\">\n"
				+ "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n"
				+ "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f6f6f6;\"><![endif]-->\n"
				+ "    \n" + "\n"
				+ "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n"
				+ "  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n"
				+ "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n"
				+ "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:500px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n"
				+ "      \n"
				+ "<!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\"width: 500px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
				+ "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 500px;display: table-cell;vertical-align: top;\">\n"
				+ "  <div style=\"height: 100%;width: 100% !important;\">\n"
				+ "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
				+ "  \n"
				+ "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
				+ "  <tbody>\n" + "    <tr>\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:29px 10px 10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n"
				+ "        \n"
				+ "  <h1 style=\"margin: 0px; line-height: 140%; text-align: center; word-wrap: break-word; font-size: 22px; font-weight: 700; \">DividendInvesting.com</h1>\n"
				+ "\n" + "      </td>\n" + "    </tr>\n" + "  </tbody>\n" + "</table>\n" + "\n"
				+ "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
				+ "  <tbody>\n" + "    <tr>\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n"
				+ "        \n" + "  <div style=\"line-height: 140%; text-align: center; word-wrap: break-word;\">\n"
				+ "    <p style=\"line-height: 140%;\">Hello " + user.getFirstName()
				+ "! Thank you for registration!<br />Confirm your account:</p>\n"
				+ "<p style=\"line-height: 140%;\"> </p>\n" + "  </div>\n" + "\n" + "      </td>\n" + "    </tr>\n"
				+ "  </tbody>\n" + "</table>\n" + "\n"
				+ "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
				+ "  <tbody>\n" + "    <tr>\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n"
				+ "        \n"
				+ "  <!--[if mso]><style>.v-button {background: transparent !important;}</style><![endif]-->\n"
				+ "<div align=\"center\">\n"
				+ "  <!--[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:37px; v-text-anchor:middle; width:101px;\" arcsize=\"11%\"  stroke=\"f\" fillcolor=\"#6719e5\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:'Cabin',sans-serif;\"><![endif]-->  \n"
				+ "    <a href=\"" + link
				+ "\" target=\"_blank\" class=\"v-button\" style=\"box-sizing: border-box;display: inline-block;font-family:'Cabin',sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #6719e5; border-radius: 4px;-webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;font-size: 14px;\">\n"
				+ "      <span style=\"display:block;padding:10px 20px;line-height:120%;\"><span style=\"line-height: 16.8px;\">Confirm</span></span>\n"
				+ "    </a>\n" + "  <!--[if mso]></center></v:roundrect><![endif]-->\n" + "</div>\n" + "\n"
				+ "      </td>\n" + "    </tr>\n" + "  </tbody>\n" + "</table>\n" + "\n"
				+ "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
				+ "  <tbody>\n" + "    <tr>\n"
				+ "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n"
				+ "        \n" + "  <div style=\"line-height: 100%; text-align: center; word-wrap: break-word;\">\n"
				+ "    <a href=\"" + link + "\" style=\"line-height: 100%;\">" + link + "</p>\n" + "  </div>\n" + "\n"
				+ "      </td>\n" + "    </tr>\n" + "  </tbody>\n" + "</table>\n" + "\n"
				+ "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" + "  </div>\n" + "</div>\n"
				+ "<!--[if (mso)|(IE)]></td><![endif]-->\n"
				+ "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" + "    </div>\n"
				+ "  </div>\n" + "</div>\n" + "\n" + "\n" + "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
				+ "    </td>\n" + "  </tr>\n" + "  </tbody>\n" + "  </table>\n" + "  <!--[if mso]></div><![endif]-->\n"
				+ "  <!--[if IE]></div><![endif]-->\n" + "</body>\n" + "\n" + "</html>\n";
	}

}
