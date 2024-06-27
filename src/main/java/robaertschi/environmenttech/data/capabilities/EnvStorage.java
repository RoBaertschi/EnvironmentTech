/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.data.capabilities;

import net.minecraft.util.Mth;

@SuppressWarnings("unused")
public class EnvStorage implements IEnvStorage {
    private final EnvType[] acceptedEnvTypes;
    private final long maxEnv;
    private long env;
    private final long maxTransfer;

    public EnvStorage(EnvType acceptedEnvType, long maxEnv, long env, long maxTransfer) {
        this.acceptedEnvTypes = new EnvType[1];
        this.acceptedEnvTypes[0] = acceptedEnvType;
        this.maxEnv = maxEnv;
        this.env = env;
        this.maxTransfer = maxTransfer;
    }

    public EnvStorage(EnvType acceptedEnvType, long maxEnv) {
        this(acceptedEnvType, maxEnv, 0, maxEnv);
    }

    public EnvStorage(EnvType[] acceptedEnvTypes, long maxEnv, long env, long maxTransfer) {
        this.acceptedEnvTypes = acceptedEnvTypes;
        this.maxEnv = maxEnv;
        this.env = env;
        this.maxTransfer = maxTransfer;
    }

    public EnvStorage(EnvType[] acceptedEnvTypes, long maxEnv) {
        this(acceptedEnvTypes, maxEnv, 0, maxEnv);
    }

    @Override
    public long receiveEnv(long amount, boolean simulate) {
        long received = Mth.clamp(this.maxEnv - this.env, 0, Math.min(amount, maxTransfer));
        if (!simulate) {
            onContentsChanged();
            env += received;
        }

        return received;
    }

    @Override
    public long getEnvStored() {
        return env;
    }

    public void setEnvStored(long env) {
        this.env = env;
    }

    @Override
    public long getMaxEnv() {
        return maxEnv;
    }

    @Override
    public EnvType[] canAcceptEnvType() {
        return acceptedEnvTypes;
    }

    public void onContentsChanged() {}
}
