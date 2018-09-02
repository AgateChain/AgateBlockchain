package com.wavesplatform.settings

import com.typesafe.config.ConfigFactory
import com.wavesplatform.matcher.MatcherSettings
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

import scala.concurrent.duration._

class MatcherSettingsSpecification extends FlatSpec with Matchers {
  "MatcherSettings" should "read values" in {
<<<<<<< HEAD
    val config = loadConfig(
      ConfigFactory.parseString(
        """Agate {
        |  directory: "/Agate"
=======
    val config = loadConfig(ConfigFactory.parseString("""waves {
        |  directory: "/waves"
>>>>>>> 4f3106f04982d02459cdc4705ed835b976d02dd9
        |  matcher {
        |    enable: yes
        |    account: "BASE58MATCHERACCOUNT"
        |    bind-address: "127.0.0.1"
        |    port: 6886
        |    min-order-fee: 100000
        |    order-match-tx-fee: 100000
        |    snapshots-interval: 1d
        |    order-cleanup-interval: 5m
        |    rest-order-limit: 100
        |    price-assets: [
        |      "Agate",
        |      "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS",
        |      "DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J"
        |    ]
<<<<<<< HEAD
        |    predefined-pairs: [
        |      {amountAsset = "Agate", priceAsset = "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS"},
        |      {amountAsset = "DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J", priceAsset = "Agate"},
        |      {amountAsset = "DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J", priceAsset = "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS"},
        |    ]
        |    max-timestamp-diff = 3h
=======
        |    max-timestamp-diff = 30d
>>>>>>> 4f3106f04982d02459cdc4705ed835b976d02dd9
        |    blacklisted-assets: ["a"]
        |    blacklisted-names: ["b"]
        |    blacklisted-addresses: ["c"]
        |    validation-timeout = 10m
        |  }
        |}""".stripMargin))

    val settings = MatcherSettings.fromConfig(config)
    settings.enable should be(true)
    settings.account should be("BASE58MATCHERACCOUNT")
    settings.bindAddress should be("127.0.0.1")
    settings.port should be(6886)
    settings.minOrderFee should be(100000)
    settings.orderMatchTxFee should be(100000)
    settings.journalDataDir should be("/Agate/matcher/journal")
    settings.snapshotsDataDir should be("/Agate/matcher/snapshots")
    settings.snapshotsInterval should be(1.day)
    settings.orderCleanupInterval should be(5.minute)
    settings.maxOrdersPerRequest should be(100)
<<<<<<< HEAD
    settings.priceAssets should be(Seq("Agate", "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS", "DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J"))
    settings.predefinedPairs should be(
      Seq(
        AssetPair.createAssetPair("Agate", "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS").get,
        AssetPair.createAssetPair("DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J", "Agate").get,
        AssetPair.createAssetPair("DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J", "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS").get
      ))
=======
    settings.priceAssets should be(Seq("WAVES", "8LQW8f7P5d5PZM7GtZEBgaqRPGSzS3DfPuiXrURJ4AJS", "DHgwrRvVyqJsepd32YbBqUeDH4GJ1N984X8QoekjgH8J"))
>>>>>>> 4f3106f04982d02459cdc4705ed835b976d02dd9
    settings.blacklistedAssets shouldBe Set("a")
    settings.blacklistedNames.map(_.pattern.pattern()) shouldBe Seq("b")
    settings.validationTimeout shouldBe 10.minutes
    settings.blacklistedAddresses shouldBe Set("c")
  }
}
