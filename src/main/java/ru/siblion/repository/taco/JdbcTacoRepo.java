package ru.siblion.repository.taco;

import org.springframework.beans.factory.annotation.Autowired;
import ru.siblion.tacos.Ingredient;
import ru.siblion.tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;



@Repository
public class JdbcTacoRepo implements TacoRepo {

    private static final Logger loggger = Logger.getLogger(JdbcTemplate.class.getCanonicalName());
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(new Ingredient
                    (ingredient,null, null), tacoId);
        }
        return taco;
    }

    /**
     * До этого не было setReturnGeneratedKeys(true). Из-за этого не укадалось
     * получить id сохраненной записи в БД с помощью KeyHolder.
     */
    private long saveTacoInfo(Taco taco) {

        loggger.log(Level.INFO, "[Taco =" +taco+"]");

        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);
        factory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = factory.newPreparedStatementCreator(Arrays.asList(
                taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        loggger.log(Level.INFO, "[keyHolder =" +keyHolder.getKey()+"]");

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbc.update(
            "insert into Taco_Ingredients (taco, ingredient) " +
                    "values (?, ?)",
            tacoId, ingredient.getId());
    }
}
