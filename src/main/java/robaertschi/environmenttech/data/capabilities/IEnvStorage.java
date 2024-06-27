/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.data.capabilities;

/**
 * ENV is pushed based, so you should not be able to extract ENV from anything.
 * This is the reason for the nonexistent extract method.
 */
@SuppressWarnings("unused")
public interface IEnvStorage {
    /**
     * Receive ENV.
     * @param amount The Amount of ENV to receive.
     * @param simulate If the operation is to only be simulated and not affect the storage amount
     * @return How much energy was accepted.
     */
    long receiveEnv(long amount, boolean simulate);

    long getEnvStored();
    long getMaxEnv();


    /**
     * @return Which ENVType's that are supported.
     */
    EnvType[] canAcceptEnvType();
}
