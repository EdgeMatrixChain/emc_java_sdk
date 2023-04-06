/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pro.edgematrix.crypto;

import org.web3j.crypto.transaction.type.ITransaction;
import org.web3j.crypto.transaction.type.LegacyTransaction;
import org.web3j.crypto.transaction.type.TransactionType;

import java.math.BigInteger;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">yellow
 * paper</a>.
 */
public class RawTelegram {

    private final ITransaction transaction;

    protected RawTelegram(final ITransaction transaction) {
        this.transaction = transaction;
    }

    protected RawTelegram(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data) {
        this(new LegacyTransaction(nonce, gasPrice, gasLimit, to, value, data));
    }

    public static RawTelegram createContractTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String init) {
        return new RawTelegram(
                LegacyTransaction.createContractTransaction(
                        nonce, gasPrice, gasLimit, value, init));
    }

    public static RawTelegram createEtherTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value) {

        return new RawTelegram(
                LegacyTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value));
    }

    public static RawTelegram createTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data) {

        return new RawTelegram(
                LegacyTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data));
    }


    public BigInteger getNonce() {
        return transaction.getNonce();
    }

    public BigInteger getGasPrice() {
        return transaction.getGasPrice();
    }

    public BigInteger getGasLimit() {
        return transaction.getGasLimit();
    }

    public String getTo() {
        return transaction.getTo();
    }

    public BigInteger getValue() {
        return transaction.getValue();
    }

    public String getData() {
        return transaction.getData();
    }

    public TransactionType getType() {
        return transaction.getType();
    }

    public ITransaction getTransaction() {
        return transaction;
    }
}
