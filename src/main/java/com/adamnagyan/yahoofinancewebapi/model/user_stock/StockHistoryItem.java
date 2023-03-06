package com.adamnagyan.yahoofinancewebapi.model.user_stock;

import com.adamnagyan.yahoofinancewebapi.model.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockHistoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	private String symbol;

	@NotNull
	private Double quantity;

	private Double transactionFee = 0.0;

	@NotNull
	private Double price;

	private LocalDate buyDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		StockHistoryItem that = (StockHistoryItem) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
