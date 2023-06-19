package com.adamnagyan.yahoofinancewebapi.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class StockBalanceSheet {

	private StatementValue endDate = new StatementValue();

	private StatementValue cash = new StatementValue();

	private StatementValue shortTermInvestments = new StatementValue();

	private StatementValue netReceivables = new StatementValue();

	private StatementValue inventory = new StatementValue();

	private StatementValue otherCurrentAssets = new StatementValue();

	private StatementValue totalCurrentAssets = new StatementValue();

	private StatementValue longTermInvestments = new StatementValue();

	private StatementValue propertyPlantEquipment = new StatementValue();

	private StatementValue goodWill = new StatementValue();

	private StatementValue otherAssets = new StatementValue();

	private StatementValue deferredLongTermAssetCharges = new StatementValue();

	private StatementValue totalAssets = new StatementValue();

	private StatementValue accountsPayable = new StatementValue();

	private StatementValue shortLongTermDebt = new StatementValue();

	private StatementValue otherCurrentLiab = new StatementValue();

	private StatementValue longTermDebt = new StatementValue();

	private StatementValue otherLiab = new StatementValue();

	private StatementValue deferredLongTermLiab = new StatementValue();

	private StatementValue minorityInterest = new StatementValue();

	private StatementValue totalCurrentLiabilities = new StatementValue();

	private StatementValue totalLiab = new StatementValue();

	private StatementValue commonStock = new StatementValue();

	private StatementValue retainedEarnings = new StatementValue();

	private StatementValue treasuryStock = new StatementValue();

	private StatementValue capitalSurplus = new StatementValue();

	private StatementValue otherStockholderEquity = new StatementValue();

	private StatementValue totalStockholderEquity = new StatementValue();

	private StatementValue netTangibleAssets = new StatementValue();

	public StockBalanceSheet add(StockBalanceSheet that) {
		return StockBalanceSheet.builder()
			.endDate(new StatementValue(this.endDate.raw, this.endDate.fmt))
			.cash(cash.add(that.cash))
			.shortTermInvestments(shortTermInvestments.add(that.shortTermInvestments))
			.netReceivables(netReceivables.add(that.netReceivables))
			.inventory(inventory.add(that.inventory))
			.otherCurrentAssets(otherCurrentAssets.add(that.otherCurrentAssets))
			.totalCurrentAssets(totalCurrentAssets.add(that.totalCurrentAssets))
			.longTermInvestments(longTermInvestments.add(that.longTermInvestments))
			.propertyPlantEquipment(propertyPlantEquipment.add(that.propertyPlantEquipment))
			.goodWill(goodWill.add(that.goodWill))
			.otherAssets(otherAssets.add(that.otherAssets))
			.deferredLongTermAssetCharges(deferredLongTermAssetCharges.add(that.deferredLongTermAssetCharges))
			.totalAssets(totalAssets.add(that.totalAssets))
			.accountsPayable(accountsPayable.add(that.accountsPayable))
			.shortLongTermDebt(shortLongTermDebt.add(that.shortLongTermDebt))
			.otherCurrentLiab(otherCurrentLiab.add(that.otherCurrentLiab))
			.longTermDebt(longTermDebt.add(that.longTermDebt))
			.otherLiab(otherLiab.add(that.otherLiab))
			.deferredLongTermLiab(deferredLongTermLiab.add(that.deferredLongTermLiab))
			.minorityInterest(minorityInterest.add(that.minorityInterest))
			.totalCurrentLiabilities(totalCurrentLiabilities.add(that.totalCurrentLiabilities))
			.totalLiab(totalLiab.add(that.totalLiab))
			.commonStock(commonStock.add(that.commonStock))
			.retainedEarnings(retainedEarnings.add(that.retainedEarnings))
			.treasuryStock(treasuryStock.add(that.treasuryStock))
			.capitalSurplus(capitalSurplus.add(that.capitalSurplus))
			.otherStockholderEquity(otherStockholderEquity.add(that.otherStockholderEquity))
			.totalStockholderEquity(totalStockholderEquity.add(that.totalStockholderEquity))
			.netTangibleAssets(netTangibleAssets.add(that.netTangibleAssets))
			.build();
	}

}
