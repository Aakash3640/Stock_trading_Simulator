package Stock_Simulation.Repository;

import Stock_Simulation.Model.Portfolio;
import Stock_Simulation.Model.Stock;
import Stock_Simulation.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // Here i is alias for stock
    @Query("SELECT i FROM Stock i WHERE LOWER(i.symbol) = LOWER(:symbol)")
    Stock findBySymbol(@Param("symbol") String symbol);
}

