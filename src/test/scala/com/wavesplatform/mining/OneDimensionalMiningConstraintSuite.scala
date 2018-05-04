package com.wavesplatform.mining

import com.wavesplatform.state.Blockchain
import com.wavesplatform.{NoShrink, TransactionGen}
import org.scalacheck.Gen
import org.scalamock.scalatest.PathMockFactory
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FreeSpec, Matchers}
import scorex.lagonaki.mocks.TestBlock
import scorex.transaction.Transaction

class OneDimensionalMiningConstraintSuite extends FreeSpec with Matchers with PropertyChecks with PathMockFactory with TransactionGen with NoShrink {
  "OneDimensionalMiningConstraint" - {
    "should be empty if the limit is 0, but not overfilled" in {
      val tank = createConstConstraint(0)
      tank.isEmpty shouldBe true
      tank.isOverfilled shouldBe false
    }

    "put(block)" - tests { (maxTxs, txs) =>
      val blocks     = txs.map(tx => TestBlock.create(Seq(tx)))
      val constraint = createConstConstraint(maxTxs, blockSize = 1)
      blocks.foldLeft(constraint)(_.put(stub[Blockchain], _))
    }

    "put(transaction)" - tests { (maxTxs, txs) =>
      val constraint = createConstConstraint(maxTxs, transactionSize = 1)
      txs.foldLeft(constraint)(_.put(stub[Blockchain], _))
    }
  }

  private def tests(toConstraint: (Int, List[Transaction]) => MiningConstraint): Unit = {
    val dontReachLimitGen: Gen[MiningConstraint] = for {
      maxTxs   <- Gen.chooseNum(1, Int.MaxValue)
      txNumber <- Gen.chooseNum(0, maxTxs - 1)
      txs      <- Gen.listOfN(math.min(txNumber, 15), randomTransactionGen)
    } yield toConstraint(maxTxs, txs)

    "multiple items don't reach the limit" in forAll(dontReachLimitGen) { updatedConstraint =>
      updatedConstraint.isEmpty shouldBe false
      updatedConstraint.isOverfilled shouldBe false
    }

    val reachSoftLimitGen: Gen[MiningConstraint] = for {
      maxTxs <- Gen.chooseNum(1, 10)
      txs    <- Gen.listOfN(maxTxs, randomTransactionGen)
    } yield toConstraint(maxTxs, txs)

    "multiple items reach the limit softly" in forAll(reachSoftLimitGen) { updatedConstraint =>
      updatedConstraint.isEmpty shouldBe true
      updatedConstraint.isOverfilled shouldBe false
    }

    val reachHardLimitGen: Gen[MiningConstraint] = for {
      maxTxs   <- Gen.chooseNum(1, 10)
      txNumber <- Gen.chooseNum(maxTxs + 1, maxTxs + 10)
      txs      <- Gen.listOfN(txNumber, randomTransactionGen)
    } yield toConstraint(maxTxs, txs)

    "multiple items reach the limit with gap" in forAll(reachHardLimitGen) { updatedConstraint =>
      updatedConstraint.isEmpty shouldBe true
      updatedConstraint.isOverfilled shouldBe true
    }
  }
}
