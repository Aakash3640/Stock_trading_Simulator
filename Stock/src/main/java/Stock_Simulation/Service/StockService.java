package Stock_Simulation.Service;

import Stock_Simulation.Model.Portfolio;
import Stock_Simulation.Model.Stock;
import Stock_Simulation.Model.Transaction;
import Stock_Simulation.Repository.PortfolioRepository;
import Stock_Simulation.Repository.StockRepository;
import Stock_Simulation.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public String buyStock(String userId, String stockSymbol, int quantity, double price) {
        Stock stock = stockRepository.findBySymbol(stockSymbol);
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found: " + stockSymbol);
        }
        double stockPrice = stock.getPrice();

        Portfolio portfolio = portfolioRepository.findByUserIdAndStockSymbol(userId, stockSymbol);

        if (portfolio == null) {
            portfolio = new Portfolio(userId, stockSymbol, 0);
        }



        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);

        // Log the transaction
        Transaction transaction = new Transaction(stockSymbol, quantity, "BUY", stockPrice);
        transactionRepository.save(transaction);

        return "Successfully bought " + quantity + " shares of " + stockSymbol;
    }

    public String sellStock(String userId, String stockSymbol, int quantity, double price) {
        Portfolio portfolio = portfolioRepository.findByUserIdAndStockSymbol(userId, stockSymbol);

        if (portfolio == null || portfolio.getQuantity() < quantity) {
            return "Not enough shares to sell";
        }

        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        portfolioRepository.save(portfolio);

        Stock stock = stockRepository.findBySymbol(stockSymbol);

        if (stock == null) {
            throw new IllegalArgumentException("Stock not found: " + stockSymbol);
        }
        double stockPrice = stock.getPrice();
        Transaction transaction = new Transaction(stockSymbol, quantity, "SELL", stockPrice);
        transactionRepository.save(transaction);

        return "Successfully sold " + quantity + " shares of " + stockSymbol;
    }



}
