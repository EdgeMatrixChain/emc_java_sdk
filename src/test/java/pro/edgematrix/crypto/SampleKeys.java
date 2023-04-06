/*
 * Copyright 2023 edgematrix Labs Ltd.
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

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * Keys generated for unit testing purposes.
 */
public class SampleKeys {

    public static final String PRIVATE_KEY_STRING =
            "03b7dfc824b0cbcfe789ec0ce4571f3460befd0490e3d0d2aad8e3c07dbcce14";
    public static final String ADDRESS = "0x0aF137aa3EcC7d10d926013ee34049AfA77382e6";
    public static final String ADDRESS_NO_PREFIX = Numeric.cleanHexPrefix(ADDRESS);


    static final BigInteger PRIVATE_KEY = Numeric.toBigInt(PRIVATE_KEY_STRING);

    static final ECKeyPair KEY_PAIR = ECKeyPair.create(PRIVATE_KEY);

    public static final Credentials CREDENTIALS = Credentials.create(KEY_PAIR);

    private SampleKeys() {
    }
}
