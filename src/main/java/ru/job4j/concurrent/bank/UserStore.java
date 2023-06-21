package ru.job4j.concurrent.bank;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean res = false;
        if (store.put(user.getId(), user) == null) {
            res = true;
        }
        return res;
    }

    public synchronized boolean update(User user) {
        boolean res = false;
        if (store.put(user.getId(), user) != null) {
            res = true;
        }
        return res;
    }

    public synchronized boolean delete(User user) {
        boolean res = false;
        if (store.remove(user.getId()) != null) {
            res = true;
        }
        return res;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean res = false;
        if (store.get(fromId).getAmount() >= amount) {
            update(new User(fromId, store.get(fromId).getAmount() - amount));
            update(new User(toId, store.get(toId).getAmount() + amount));
            res = true;
        }
        return res;
    }

    public static void main(String[] args) {
        UserStore stoge = new UserStore();
        stoge.add(new User(1, 100));
        stoge.add(new User(2, 200));
        stoge.transfer(1, 2, 50);
        System.out.println(stoge.store.get(1).getAmount());
        System.out.println(stoge.store.get(2).getAmount());
    }
}
