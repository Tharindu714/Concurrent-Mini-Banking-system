package util;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.Account;

public class Bank {
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public Account getAccount(int id) {
        return accounts.get(id);
    }
}
