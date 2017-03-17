package xpoint.api.service

/**
  * Created by szhu.spark@gmail.com on 01/03/2017.
  */
trait TokenService {
  /**
    *
    * @param token
    * @return (credential, companies, lang)
    */
  def scope(token: String): (String, String, String) ={
    token match {
      case "test_token_201_11" =>
        ("test", "207,201,11", "en")
      case _ =>
        ("test", "201,11", "en")
    }
  }
}
