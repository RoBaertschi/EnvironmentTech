/*
 *  EnvironmentTech MC Mod
    Copyright (C) 2024 Robin Bärtschi and Contributors

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, by version 3 of the License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package xyz.robaertschi.environmenttech.data.capabilities;

import net.minecraft.util.Mth;

@SuppressWarnings("unused")
public class EnvStorage implements IEnvStorage {
    private final long maxEnv;
    private long env;
    private final long maxTransfer;

    public EnvStorage(long maxEnv) {
        this(maxEnv, 0, maxEnv);
    }

    public EnvStorage(long maxEnv, long env, long maxTransfer) {
        this.maxEnv = maxEnv;
        this.env = env;
        this.maxTransfer = maxTransfer;
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

    public void onContentsChanged() {}
}
