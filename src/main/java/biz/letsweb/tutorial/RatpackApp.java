package biz.letsweb.tutorial;

import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

/**
 * @author tomasz
 */
public class RatpackApp {
    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(bindings->bindings.module(HikariModule.class, config->{
                    config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
                    config.addDataSourceProperty("URL", "jdbc:h2:mem:ratpackdb;INIT=RUNSCRIPT FROM 'classpath:init.sql'");
                })))
                .serverConfig(c->c.baseDir(BaseDir.find()).build())
                .handlers(chain -> chain
                        .files(file->file.dir("static").indexFiles("index.html"))
                        .get(ctx -> ctx.render("Hello World!"))
                        .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))))
        ;
    }
}
