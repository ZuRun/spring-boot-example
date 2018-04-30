package me.zuhr.demo.zuul.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.zuhr.demo.zuul.configuration.JwtConfiguration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Reserved claims（保留），它的含义就像是编程语言的保留字一样，属于JWT标准里面规定的一些claim。JWT标准里面定好的claim有：
 * <p>
 * iss(Issuer)：代表这个JWT的签发主体；
 * sub(Subject)：代表这个JWT的主体，即它的所有人；
 * aud(Audience)：代表这个JWT的接收对象；
 * exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
 * nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
 * iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
 * jti(JWT ID)：是JWT的唯一标识。
 *
 * @author zurun
 * @date 2018/4/24 23:01:33
 */
@Component
public class JwtHelper {


    /**
     * 生成token
     *
     * @param jwt
     * @param params 自定义载荷（payload）
     * @return
     */
    public static String createToken(JwtConfiguration jwt, Map params) {
        long nowMillis = System.currentTimeMillis();

        //添加构成JWT的参数
        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT")
//                .claim("userid", userId)
                .setIssuer(jwt.getIssuer())
                .setAudience(jwt.getAudience())
                .signWith(SignatureAlgorithm.HS256, jwt.getSecret())
                .setExpiration(new Date(nowMillis + jwt.getExpiresMillisecond()));
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            jwtBuilder.claim((String) entry.getKey(), entry.getValue());
        }
        return jwtBuilder.compact();

    }

}
