package com.adamnagyan.yahoofinancewebapi.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewPasswordToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	private String token;

	private LocalDateTime createdAt;

	private LocalDateTime expiresAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		NewPasswordToken that = (NewPasswordToken) o;

		if (!Objects.equals(id, that.id))
			return false;
		if (!Objects.equals(token, that.token))
			return false;
		if (!Objects.equals(createdAt, that.createdAt))
			return false;
		if (!Objects.equals(expiresAt, that.expiresAt))
			return false;
		return Objects.equals(user, that.user);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (token != null ? token.hashCode() : 0);
		result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
		result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
		result = 31 * result + (user != null ? user.hashCode() : 0);
		return result;
	}

}
