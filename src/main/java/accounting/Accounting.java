package accounting;

import networking.Client;
import windows.PopUpWindow;
import windows.SceneEnum;

public class Accounting {
    private Client client;
    private int attempts;

    public Accounting() {
        client = new Client("10.6.12.137", 3367);
        attempts = 3;
    }

    public SceneEnum cardValidator(String cardNumber) {
        long number;
        if (cardNumber.length() < 16) {
            new PopUpWindow("Too short card number. Required 16 numbers.");
            return SceneEnum.CARD;
        }
        if (cardNumber.length() > 16) {
            new PopUpWindow("Too long card number. Required 16 numbers.");
            return SceneEnum.CARD;
        }
        try {
            number = Long.parseLong(cardNumber);
        } catch (NumberFormatException ex) {
            new PopUpWindow("Entered wrong input.");
            return SceneEnum.CARD;
        }
        if (checkNumber(number)) {
            String respond = client.sendAndReceiveRequest(Request.CHECK, cardNumber);
            if (respond.equalsIgnoreCase("true")) {
                return SceneEnum.PIN;
            } else {
                new PopUpWindow("Your card is not supported by our system.");
            }
        } else {
            new PopUpWindow("Wrong card number");
        }
        return SceneEnum.CARD;
    }

    private boolean checkNumber(long cardNumber) {
        int sum = calculateSum(cardNumber / 10);
        long last = cardNumber % 10;
        sum = sum % 10;
        return ((10 - sum) % 10 == (int) last);
    }

    private int calculateSum(long cardNumber) {
        int sum = 0;
        boolean isOdd = true;
        while (cardNumber > 0) {
            if (isOdd) {
                int tmp = (int) (cardNumber % 10) * 2;
                sum += (tmp < 10) ? tmp : (tmp / 10) + (tmp % 10);
                isOdd = false;
                cardNumber /= 10;
            } else {
                sum += (int) (cardNumber % 10);
                isOdd = true;
                cardNumber /= 10;
            }
        }
        return sum;
    }

    public SceneEnum pinValidator(String pin) {
        if (pin.length() == 4) {
            String respond = client.sendAndReceiveRequest(Request.PIN, pin);
            if (respond.equalsIgnoreCase("PIN correct")) {
                return SceneEnum.MENU;
            } else {
                attempts--;
                if (attempts != 0) {
                    new PopUpWindow("Wrong PIN. Remaining attempts: " + attempts + ".");
                } else {
                    new PopUpWindow("Out of attempts. Returning to Card screen.");
                    return SceneEnum.CARD;
                }
            }
        } else {
            attempts--;
            if (attempts != 0) {
                new PopUpWindow("Too long pin number. Remaining attempts: " + attempts + ".");
            } else {
                new PopUpWindow("Out of attempts. Returning to Card screen.");
                return SceneEnum.CARD;
            }
        }
        return SceneEnum.PIN;
    }

    public String getBalance() {
        return client.sendAndReceiveRequest(Request.BALANCE);
    }

    public SceneEnum deposit(String depot) {
        try {
            int deposit = Integer.parseInt(depot);
            if (deposit % 10 == 0 && (deposit >= 40 || deposit == 20)) {
                client.sendAndReceiveRequest(Request.DEPOSIT, Integer.toString(deposit));
                new PopUpWindow("Balance added");
                return SceneEnum.MENU;
            } else {
                new PopUpWindow("Enter money to deposit. Only available multiplication of 20, 50, 100.");
                return SceneEnum.DEPOSIT;
            }
        } catch (NumberFormatException ex) {
            new PopUpWindow("Enter valid value.");
            return SceneEnum.DEPOSIT;
        }
    }

    public SceneEnum withdraw(String depot) {
        try {
            int deposit = Integer.parseInt(depot);
            if (deposit % 10 == 0 && (deposit >= 40 || deposit == 20)) {
                double balance = Double.parseDouble(getBalance());
                if (balance > deposit) {
                    client.sendAndReceiveRequest(Request.WITHDRAW, Integer.toString(deposit));
                    new PopUpWindow("Money withdrawn.");
                    return SceneEnum.MENU;
                } else {
                    new PopUpWindow("Not enough funds.");
                    return SceneEnum.WITHDRAW;
                }
            } else {
                new PopUpWindow("Enter money to withdraw. Only available multiplication of 20, 50, 100.");
                return SceneEnum.WITHDRAW;
            }
        } catch (NumberFormatException ex) {
            new PopUpWindow("Enter valid value.");
            return SceneEnum.WITHDRAW;
        }
    }

    public void close() {
        client.closeConnection();
    }

    public void shutdown() {
        client.sendAndReceiveRequest(Request.SHUTDOWN);
        close();
    }
}
