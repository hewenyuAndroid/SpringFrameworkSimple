package com.example;

import com.example.dao.AccountDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class AccountDaoCaseTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void testSubtractBalanceById() {
        System.out.println("AccountDaoCaseTest: testSubtractBalanceById(), begin.");
        int result = accountDao.subtractBalanceById(1, new BigDecimal("20"));
        System.out.println("testSubtractBalanceById(), result: " + result);
        System.out.println("AccountDaoCaseTest: testSubtractBalanceById(), end.");
    }

}
