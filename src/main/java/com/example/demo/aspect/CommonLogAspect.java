package com.example.demo.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonLogAspect {

	/* ロガー(Logback)*/
	public final Logger log = LoggerFactory.getLogger(CommonLogAspect.class);
	
	/**
	 * 指定したメソッドの開始、終了ログを出力します。
	 * 
	 * <p>また例外発生時は加えて例外内容をログに出力します。
	 *
	 * @param jp 処理を挿入する場所の情報
	 * @return 指定したメソッドの戻り値
	 * @throws Throwable メソッド実行時にcatchした例外
	 */
	@Around("execution(* com.example.demo..*(..))")
	public Object writeLog(ProceedingJoinPoint jp) throws Throwable{
		Object returnObj = null;
		//開始ログを出力
		log.info("start:" + jp.getSignature().toString());
		
		try {
			// jointPoint のメソッドを実行
			returnObj = jp.proceed();
		} catch(Throwable t) {
			// エラーログを出力
			log.error(t.toString());
			throw t;
		}
		
		// 終了ログを出力
		log.info("end:" + jp.getSignature().toString());
		
		return returnObj;
	}
}
