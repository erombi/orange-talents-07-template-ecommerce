package br.com.zupacademy.eduardo.mercadolivre.infra;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ExecuteTransaction {

    @Transactional
    public void inTransaction(Runnable runnable) {
        runnable.run();
    }
}
