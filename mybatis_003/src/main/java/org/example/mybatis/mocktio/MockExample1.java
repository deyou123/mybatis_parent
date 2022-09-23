package org.example.mybatis.mocktio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author DeYou
 * @date 2022/9/22
 */
public class MockExample1 {
    @Data
    @AllArgsConstructor
    public static class Stock{
        private String id;
        private String name;
        private double price;

    }
    public static class Portfolio{

        public void setStocks(List<Stock> stocks) {
        }

        public void setStockService(StockService stockServiceMock) {
        }

        public double getMarketValue() {
        }
    }


    public static class StockService{
       public double getPrice(Stock stock){
           return stock.getPrice();
       }
    }

    public void test01(){
        Portfolio portfolio = new Portfolio();
        List<Stock> stocks = new ArrayList<Stock>();
        Stock googleStock = new Stock("1","Google", 10);
        Stock microsoftStock = new Stock("2","Microsoft",100);
        stocks.add( googleStock );
        stocks.add( microsoftStock );
        StockService stockServiceMock = mock(StockService.class);

        when(stockServiceMock.getPrice(googleStock)).thenReturn(50.00);
        when(stockServiceMock.getPrice(microsoftStock)).thenReturn(1000.00);

        portfolio.setStocks(stocks);
        portfolio.setStockService(stockServiceMock);
        double marketValue = portfolio.getMarketValue();

        System.out.println("Market value of the portfolio: "+ marketValue);
    }
}