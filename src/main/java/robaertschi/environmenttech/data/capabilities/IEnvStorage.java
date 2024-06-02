package robaertschi.environmenttech.data.capabilities;

/**
 * ENV is pushed based, so you should not be able to extract ENV from anything.
 * This is the reason for the nonexistent extract method.
 */
public interface IEnvStorage {
    /**
     * Receive ENV.
     * The type is provided in {@link EnvCapabilityContext}
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
