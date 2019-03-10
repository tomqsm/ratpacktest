package biz.letsweb.tutorial;

import org.h2.jdbcx.JdbcDataSource;
import ratpack.handling.Context;
import ratpack.handling.Handler;

/**
 * @author tomasz
 */
public class Example1Handler implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        JdbcDataSource jdbcDataSource;
        ctx.getResponse().send("Hello Example1 Handler");
    }
}
