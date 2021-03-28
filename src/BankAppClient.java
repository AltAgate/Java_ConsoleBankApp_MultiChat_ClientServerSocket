
//--------------------------------------------------------------------------------------------------------------KOPIA

public class BankAppClient {
    //BANK APP CLIENT VARIABLES
    private  String CLIENT_ID;
    private  String ACCOUNT_NR;
    private  String NAME;
    private  String SUBNAME;
    private  double BALANCE;
    private  String[] NAMELIST = {"Bob", "Tom", "Adam", "Richard" };
    private  String[] SUBNAMELIST = {"Hendriks", "Wirght", "Griffin", "Cole"};

    public BankAppClient() {
        CLIENT_ID = getRandomClientId() ;
        ACCOUNT_NR = getRandomAccountNr();
        SUBNAME = getRandomSubName();
        NAME = getRandomName();
        BALANCE = 0;
    }



    //GET SET FUNCTIONS
    public  String getClientId() { return CLIENT_ID; }
    public  String getAccountNr() { return ACCOUNT_NR;}
    public  String getName() {return NAME;}
    public  String getSubName() {return SUBNAME;}
    public  double getBalance() {return BALANCE;}

    //GET INFO FUNCTION
    public String getInfo() {
        String result = "Your client is : " + getName() + " " + getSubName() + " with ID: " + getClientId() + " " + "His/Hers account nr. is: " + getAccountNr() + " with balance: " + getBalance() + "$";
        return result;
    }


    //BALANCE FUNCTIONS
    public void deposit(double value) {
        if(value > 0) {
            System.out.println(NAME + " have deposited: " + value + " on balance " + getBalance());
            BALANCE += value;
        }
        else {
            System.out.println("Client cant deposit 0 or less cash");
        }
    }

    public void withdraw(double value) {
        if(BALANCE>=value) {
            System.out.println(NAME + " have withdrawed " + value + " on balance " + getBalance());
            BALANCE = BALANCE - value;
            System.out.println(NAME + " have withdraw: " + value + "$");
        }
        else {
            System.out.println("You cant withdraw more than your balance | Your balance: " + BALANCE +", Withdraw request: "+ value);
        }
    }

    public void send(BankAppClient sendToClient, double value) {
        if(BALANCE >= value) {
            BALANCE = BALANCE - value;
            sendToClient.BALANCE = sendToClient.getBalance() + value;
            System.out.println(NAME + " send " + value + "$ to " + sendToClient.getName());
            System.out.println("[RESULT] " + NAME + " balance: " + (BALANCE + value) + " - " + value + " = " + BALANCE + ", " + sendToClient.getName() + " balance: " + (sendToClient.BALANCE - value) + " + " + value + " = " + sendToClient.BALANCE);
        }
    }


    //GET RANDOM FUNCTIONS
    private String getRandomClientId() {
        String result = "";
        for(int i = 0; i < 5; i++) {
            result += String.valueOf((int) (Math.floor(Math.random() * 10)));
        }
        return result;
    }

    private String getRandomAccountNr() {
        String accountNR = "";
        for (int i = 0; i < 16; i++) {
            int random = (int) (Math.random() * 10);
            if(i%4==0 && i!= 0) {
                accountNR += " ";
            }
            else {
                accountNR += random;
            }
        }
        return accountNR;
    }

    private String getRandomSubName() { return SUBNAMELIST[(int) Math.floor((Math.random() * SUBNAMELIST.length + 1) - 1) ]; }

    private String getRandomName() {
        return NAMELIST[(int) Math.floor((Math.random() * NAMELIST.length + 1) - 1) ];
    }

}
