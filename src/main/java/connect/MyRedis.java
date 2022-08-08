package connect;

import redis.clients.jedis.Jedis;
import commons.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyRedis {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis(Constants.REDIS_SERVER_HOST, Constants.REDIS_SERVER_PORT)) {
            jedis.auth(Constants.REDIS_SERVER_PASSWORD);
            jedis.del("foo", "bar");

            jedis.mset("foo", "foovalue", "bar", "barvalue"); // foo=foovalue, bar=barvalue
            String[] keys = {"foo", "bar"};
            List<String> values = jedis.mget(keys);


        } catch (Exception e) {
            System.out.println("Redis error: " + e);
        }
    }
}
