package robaertschi.environmenttech.data.capabilities;

import net.minecraft.util.Mth;

// TODO: Add max receive or something
public class EnvStorage implements IEnvStorage {
    private final EnvType[] acceptedEnvTypes;
    private final long maxEnv;
    private long env;

    public EnvStorage(EnvType acceptedEnvType, long maxEnv, long env) {
        this.acceptedEnvTypes = new EnvType[1];
        this.acceptedEnvTypes[0] = acceptedEnvType;
        this.maxEnv = maxEnv;
        this.env = env;
    }

    public EnvStorage(EnvType acceptedEnvType, long maxEnv) {
        this(acceptedEnvType, maxEnv, 0);
    }

    public EnvStorage(EnvType[] acceptedEnvTypes, long maxEnv, long env) {
        this.acceptedEnvTypes = acceptedEnvTypes;
        this.maxEnv = maxEnv;
        this.env = env;
    }

    public EnvStorage(EnvType[] acceptedEnvTypes, long maxEnv) {
        this(acceptedEnvTypes, maxEnv, 0);
    }

    @Override
    public long receiveEnv(long amount, boolean simulate) {
        long received = Mth.clamp(this.maxEnv - this.env, 0, amount);
        if (!simulate)
            env += received;

        return received;
    }

    @Override
    public long getEnvStored() {
        return env;
    }

    @Override
    public long getMaxEnv() {
        return maxEnv;
    }

    @Override
    public EnvType[] canAcceptEnvType() {
        return acceptedEnvTypes;
    }
}
