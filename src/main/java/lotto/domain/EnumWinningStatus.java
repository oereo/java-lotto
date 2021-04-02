package lotto.domain;

import lotto.domain.lottoticket.NumberOfLottoTicket;
import lotto.domain.lottowinningresult.LottoWinningBonusBallResult;
import lotto.domain.lottowinningresult.LottoWinningResult;
import lotto.ui.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnumWinningStatus {
    private static final int NOT_MATCH_LOTTO = 0;
    private ArrayList<Integer> lottoPrices = new ArrayList<>();
    private Map<Integer, Integer> mappingLottoWithBonusBall = new HashMap<>();
    private Printer printer = new Printer();

    public ArrayList<Integer> getLottoPrices(LottoFactory lottoFactory, NumberOfLottoTicket numberOfLottoTicket){
        LottoWinningResult lottoWinningResults = lottoFactory.getLottoWinningResult();
        LottoWinningBonusBallResult lottoWinningBonusBallResult = lottoFactory.getLottoWinningBonusBallResult();

        for (int i = 0; i < numberOfLottoTicket.getNumberOfLottoTicket(); i++) {
            int lottoMatchedNumber = lottoWinningResults.getLottoWinningResult().get(i);
            Boolean lottoBonusBallNumber = lottoWinningBonusBallResult.getLottoWinningBonusBallResult().get(i);
            getWinningLottoTicketPrices(lottoMatchedNumber, lottoBonusBallNumber);
        }
        printer.printAllMatchedLottoResult(getMappingLottoWithBonusBall());
        return lottoPrices;
    }

    private void getWinningLottoTicketPrices(int lottoMatchedNumber, boolean lottoBonusBallNumber) {
        for(WinningStatus winningStatus: WinningStatus.values()){
            int matchedWinningNumberCount = winningStatus.getMatchCount();
            boolean isMatchedBonusBallCount = winningStatus.hasBonusBall();
            makeWinningLottoTicket(lottoMatchedNumber, matchedWinningNumberCount, isMatchedBonusBallCount, lottoBonusBallNumber, winningStatus);
        }
    }

    private void makeWinningLottoTicket(
            int lottoMatchedNumber,
            int matchedWinningNumberCount,
            boolean isMatchedBonusBallCount,
            boolean lottoBonusBallNumber,
            WinningStatus winningStatus
    ){
        if((lottoMatchedNumber == matchedWinningNumberCount) && (isMatchedBonusBallCount == lottoBonusBallNumber)){
            lottoPrices.add(winningStatus.getWinningMoney());
        }
    }

    private Map<Integer, Integer> getMappingLottoWithBonusBall() {
        for (Integer key: lottoPrices
             ) {
            mappingLottoWithBonusBall.put(key, mappingLottoWithBonusBall.getOrDefault(key, 0)+1);
        }
        return mappingLottoWithBonusBall;
    }

}
