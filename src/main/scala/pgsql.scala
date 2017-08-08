import java.sql.{Connection, DriverManager}
import java.text.SimpleDateFormat
import java.util.{Date, Timer, TimerTask}
import dispatch._
//
import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.pool.{ConnectionPool, PoolConfiguration}
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory

import scala.concurrent.ExecutionContext.Implicits.global
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.util.Properties.{getClass, _}

object pgsql extends  App {
  private val log = LoggerFactory.getLogger(getClass)

  def indexDocuments(pool : ConnectionPool[PostgreSQLConnection]) : Boolean = {

      if (!pool.isConnected)
        println("***** postgres not connected ******")
      else
        println("****** postgres connected  *******")
      pool.sendPreparedStatement("INSERT INTO test (text) VALUES(? ) ", Seq("hello" )  ).map {
        case _ => {
          log.debug("query returned");
        }
      }

    true
  }
  implicit def function2TimerTask(f: () => Unit): TimerTask = {
    return new TimerTask {
      def run() = f()
    }
  }

  private val pgsqlHost = envOrElse("PG_HOST", "localhost")
  private val pgsqlPort = envOrElse("PG_PORT", "5432").toInt
  private val pgsqlUser = envOrElse("PG_USER", "dumb")
  private val pgsqlPassword = envOrElse("PG_PASSWORD", "test_password_please_ignore")
  private val pgsqlDatabase = envOrElse("PG_DB", "sor")
  private val timerInterval = envOrElse("TIMER_INTERVAL", "10000").toInt
  private val pgConfig = new Configuration(username = pgsqlUser, password = Some(pgsqlPassword), host = pgsqlHost, database = Some(pgsqlDatabase))
  private val pgFactory = new PostgreSQLConnectionFactory( pgConfig )
  private val pgPoolConfig = PoolConfiguration(30, 5000, 100000)
  val pgPool = new ConnectionPool(pgFactory, pgPoolConfig)
  def timerTask() = indexDocuments(pgPool)

  pgPool.sendQuery("CREATE TABLE IF NOT EXISTS test ( id SERIAL, text TEXT);")
  val timer = new Timer()
  // schedule for 5 minuntes
  timer.schedule(function2TimerTask(timerTask),timerInterval, timerInterval)

  while (true) {
    val svc = url(s"http://scooterlabs.com/echo.json")


    Http.default(svc OK as.String).either.map {
      case Left(error) => {
        log.error(s"Error getting data from endpoint: ${error.toString}")
        false
      }
      case Right(response) => {
        log.debug(response)
        true
      }
    }
    Thread.sleep(1000)

  }
}
