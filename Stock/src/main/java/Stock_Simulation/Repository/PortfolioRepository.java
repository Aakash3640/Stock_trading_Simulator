package Stock_Simulation.Repository;

import Stock_Simulation.Model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio findByUserIdAndStockSymbol(String userId, String stockSymbol);
}
