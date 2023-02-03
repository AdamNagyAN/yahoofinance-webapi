package com.adamnagyan.yahoofinancewebapi.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ConfirmationToken {

  @Id
  @GeneratedValue
  private Long id;
  private String token;
  private LocalDateTime createdAt;
  private LocalDateTime expiresAt;
  private LocalDateTime confirmedAt;
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  private User user;
}
