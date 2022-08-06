import redis.clients.jedis.Jedis;

public class MyRedis {
    public static void main(String[] args) {
        System.out.println(1);
        try (Jedis jedis = new Jedis("119.23.224.219", 6379)) {
            jedis.auth("123456");
            jedis.flushAll();
            String x = jedis.get("a");
            System.out.println(x);
        } catch (Exception e) {
            System.out.println("Redis error: " + e);
        }
    }
}
