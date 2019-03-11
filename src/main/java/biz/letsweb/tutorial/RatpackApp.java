package biz.letsweb.tutorial;

import jooq.tables.Users;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;
import ratpack.exec.util.SerialBatch;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.http.Response;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @author tomasz
 */
public class RatpackApp {
    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(bindings -> bindings.module(HikariModule.class, config -> {
                    config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
                    config.addDataSourceProperty("URL", "jdbc:h2:mem:ratpackdb;INIT=RUNSCRIPT FROM 'classpath:init.sql'");
                })))
                .serverConfig(configBuilder -> configBuilder
                        .development(true)
                        .port(5050)
                        .threads(1)
                        .baseDir(BaseDir.find()).build())
                .handlers(chain -> chain
                        .files(file -> file.dir("static").indexFiles("index.html"))
                        .get(ctx -> ctx.render("Hello World!"))
                        .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                        .get("users/:id", ctx ->
                                {
                                    final DataSource dataSource = ctx.get(DataSource.class);
                                    DSLContext dslContext = DSL.using(dataSource, SQLDialect.H2);
                                    SelectJoinStep<Record> from = dslContext.select().from(Users.USERS);
                                    final Promise<List<Map<String, Object>>> listPromise = Blocking.get(from::fetchMaps);
                                    listPromise.then(list -> {
                                        ctx.render(Jackson.json(list));
                                    });
                                }
                        )))
        ;
    }
}
