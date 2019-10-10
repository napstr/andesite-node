package andesite.node.util;

import com.sedmelluq.discord.lavaplayer.tools.io.HttpConfigurable;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValueType;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class HttpConfigurator {
    public static void configure(@Nonnull Config config, @Nonnull HttpConfigurable httpConfigurable) {
        httpConfigurable.configureBuilder(builder -> {
            if(config.hasPath("proxy")) {
                builder.setProxy(HttpHost.create(config.getString("proxy")));
            }
            if(config.hasPath("user-agent")) {
                builder.setUserAgent(config.getString("user-agent"));
            }
            if(config.hasPath("headers")) {
                var headers = new ArrayList<Header>();
                config.getConfig("headers").entrySet().forEach(h -> {
                    var v = h.getValue();
                    if(v.valueType() != ConfigValueType.STRING) {
                        throw new IllegalArgumentException("Invalid header " + h.getKey() + ": expected " +
                                "value to be a string, got " + v.valueType().name().toLowerCase());
                    }
                    headers.add(new BasicHeader(h.getKey(), (String) v.unwrapped()));
                });
                builder.setDefaultHeaders(headers);
            }
            if(config.hasPath("cookies")) {
                var store = new BasicCookieStore();
                config.getConfigList("cookies").forEach(c -> {
                    var name = c.getString("name");
                    var value = c.getString("value");
                    var cookie = new BasicClientCookie(name, value);
                    if(c.hasPath("domain")) {
                        cookie.setDomain(c.getString("domain"));
                    }
                    if(c.hasPath("path")) {
                        cookie.setPath(c.getString("path"));
                    }
                    if(c.hasPath("secure")) {
                        cookie.setSecure(c.getBoolean("secure"));
                    }
                    if(c.hasPath("comment")) {
                        cookie.setComment(c.getString("comment"));
                    }
                    if(c.hasPath("version")) {
                        cookie.setVersion(c.getInt("version"));
                    }
                    if(c.hasPath("attributes")) {
                        c.getConfig("attributes").entrySet().forEach(a -> {
                            var v = a.getValue();
                            if(v.valueType() != ConfigValueType.STRING) {
                                throw new IllegalArgumentException("Invalid attribute " + a.getKey() + ": expected " +
                                        "value to be a string, got " + v.valueType().name().toLowerCase());
                            }
                            cookie.setAttribute(a.getKey(), (String) v.unwrapped());
                        });
                    }
                    store.addCookie(cookie);
                });
                builder.setDefaultCookieStore(store);
            }
        });
    }
}
