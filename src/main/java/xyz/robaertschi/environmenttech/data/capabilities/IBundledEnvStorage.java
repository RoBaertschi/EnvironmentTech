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

/**
 * ENV is pushed based, so you should not be able to extract ENV from anything.
 * This is the reason for the nonexistent extract method.
 * This is currently the same as {@link IEnvStorage}. This will however change sometime. Also they are not the same.
 * Bundled Env Storage is per Cable, which has a {@link net.minecraft.core.Direction} while the Normal Storage doesn't.
 */
public interface IBundledEnvStorage {
    /**
     * Receive ENV.
     * @param amount The Amount of ENV to receive.
     * @param simulate If the operation is to only be simulated and not affect the storage amount
     * @return How much energy was accepted.
     */
    long receiveEnv(long amount, boolean simulate);

    long getEnvStored();
    long getMaxEnv();
}
