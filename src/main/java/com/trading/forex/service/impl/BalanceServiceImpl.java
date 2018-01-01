package com.trading.forex.service.impl;

import com.oanda.v20.Context;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.trading.forex.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by wf on 10/20/2017.
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    private Double beginBalance = null;
    private Double solde = null;
    private Double maxloss = 0D;
    private Double maxProfit = 0D;
    @Autowired
    public Context context;

    @Autowired
    public AccountID accountID;


    @Override
    @Scheduled(fixedDelay = 5000)
    public void updateBalanceInfos() throws ExecuteException, RequestException {
            double current=context.account.get(accountID).getAccount().getBalance().doubleValue();
            if (null == beginBalance) {
                beginBalance = current;
                solde = 0.0;
                return;
            }
            solde = current - beginBalance;
            if (solde > maxProfit) {
                maxProfit = solde;
            }
            if (solde < maxloss) {
                maxloss = solde;
            }

    }

    @Override
    public void reset(){

        beginBalance=null;
        solde = null;
        maxloss = 0D;
        maxProfit = 0D;
    }


    @Override
    public Double getSolde() {
        return solde;
    }

    @Override
    public Double getMaxloss() {
        return maxloss;
    }

    @Override
    public Double getMaxProfit() {
        return maxProfit;
    }
}
