package connect;

import redis.clients.jedis.Jedis;
import commons.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyRedis {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis(Constants.REDIS_SERVER_HOST, Constants.REDIS_SERVER_PORT)) {
            jedis.auth(Constants.REDIS_SERVER_PASSWORD);

            jedis.del("foo");
            System.out.println(jedis.setnx("foo", "bar")); // 1
            System.out.println(jedis.setnx("foo", "bar")); // 0
        } catch (Exception e) {
            System.out.println("Redis error: " + e);
        }
    }
}
