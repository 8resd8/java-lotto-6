package lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lotto.UserInputHandler.DIVISION_ROLE;

public class WinningStatistics {
    private final List<Lotto> lottoTickets;
    private final List<Integer> matchingNumber = new ArrayList<>(Collections.nCopies(7, 0));

    public WinningStatistics() {
        this.lottoTickets = new ArrayList<>();
    }

    // 로또 구입할 금액 입력
    public void purchaseLottoTickets(int totalAmount) {
        int ticket = totalAmount / DIVISION_ROLE; // 몇 장인지 확인
        System.out.println(ticket + "개를 구매했습니다.");

        for (int i = 0; i < ticket; i++) {
            purchaseLottoPrint();
        }
    }

    // 구매한 갯수 만큼 출력
    public void purchaseLottoPrint() {
//        List<Integer> uniqueNumbers = Lotto.generateLottoNumbers();
        //        Lotto lotto = new Lotto(uniqueNumbers);
//        List<Integer> numbers = lotto.getNumbers(); // 이 번호를 넘겨서 계산해야한다.
        Lotto lotto = Lotto.generateLottoNumbers();
        List<Integer> numbers = lotto.getNumbers();

        LottoResultPrinter lottoResultPrinter = new LottoResultPrinter();
        lottoResultPrinter.printAllLotto(numbers);
        lottoTickets.add(lotto);
    }

    // 일치한 갯수 확인
    public void calculateStatistics(List<Integer> winningNumber, int bonusNumber, int userCost) {
        for (Lotto lotto : lottoTickets) {
            calculateMatchingNumbers(lotto, winningNumber, bonusNumber);
        }
        LottoResultPrinter resultPrinter = new LottoResultPrinter();
        resultPrinter.printResult(matchingNumber, userCost);

    }

    private void calculateMatchingNumbers(Lotto lotto, List<Integer> winningNumber, int bonusNumber) {
        int count = 0;
        List<Integer> lottoNumbers = lotto.getNumbers();

        for (int number : lottoNumbers) {
            if (winningNumber.contains(number)) {
                count++;
            }
        }

        if (count >= 3) {
            int currentValue = matchingNumber.get(count);
            matchingNumber.set(count, currentValue + 1);
        }
        // 5개일치 + 보너스번호 일치 확인
        if (count == 5 && lotto.getNumbers().contains(bonusNumber)) {
            int currentValue = matchingNumber.get(count);
            matchingNumber.set(0, currentValue + 1);
        }

    }


    public void start() {
        UserInputHandler userInputHandler = new UserInputHandler();
        int userCost = userInputHandler.inputUserLottoPurchase(); // 구입금액 입력하기
        System.out.println();

        purchaseLottoTickets(userCost); // 구매한 금액만큼 로또 구입후 출력
        System.out.println();
        List<Integer> winningNumber = userInputHandler.inputUserWinningNumbers(); // 당첨 번호 입력
        System.out.println();
        int bonusNumber = userInputHandler.inputUserBonusNumber(); // 보너스 번호 입력
        System.out.println();
        calculateStatistics(winningNumber, bonusNumber, userCost); // 당첨 통계
    }
}
