package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.gate.CreditGate;
import ru.netology.gate.PaymentGate;

import java.sql.*;

public class SQLHelper {


    private static QueryRunner runner = new QueryRunner();

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");
    private static Connection connection;

    public static Connection getConnect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    @SneakyThrows
    public static String getPaymentApprovedStatus() {
        var codeSQL = "SELECT * FROM payment_entity JOIN order_entity ON transaction_id = payment_id where status = 'APPROVED'";
        try (Connection connection = getConnect()) {
            var result = runner.query(connection, codeSQL, new BeanHandler<>(PaymentGate.class));
            return result.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static String getPaymentDeclinedStatus() {
        var codeSQL = "SELECT * FROM payment_entity JOIN order_entity ON transaction_id = payment_id where status = 'DECLINED'";
        try (Connection connection = getConnect()) {
            var result = runner.query(connection, codeSQL, new BeanHandler<>(PaymentGate.class));
            return result.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }


    @SneakyThrows
    public static String getCreditApprovedStatus() {
        var codeSQL = "SELECT * FROM credit_request_entity JOIN order_entity ON bank_id = credit_id where status = 'APPROVED'";
        try (Connection connection = getConnect()) {
            var result = runner.query(connection, codeSQL, new BeanHandler<>(CreditGate.class));
            return result.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static String getCreditDeclinedStatus() {
        var codeSQL = "SELECT * FROM credit_request_entity JOIN order_entity ON bank_id = credit_id where status = 'DECLINED'";
        try (Connection connection = getConnect()) {
            var result = runner.query(connection, codeSQL, new BeanHandler<>(CreditGate.class));
            return result.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConnect();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }
}
