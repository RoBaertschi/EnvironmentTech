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
package xyz.robaertschi.environmenttech.unittest;

import org.junit.jupiter.api.Test;

import xyz.robaertschi.environmenttech.client.screen.ProgressArrowUtils;

import static org.assertj.core.api.Assertions.*;

public class TestProgressBar {
    int gsp(int progress, int maxProgress) {
        return ProgressArrowUtils.getScaledProgress(progress, maxProgress);
    }

    @Test
    void testScales() {
        int scale1 = ProgressArrowUtils.getScaledProgress(1, 24);
        assertThat(scale1).isEqualTo(1);
        assertThat(gsp(24, 24)).isEqualTo(24);
        assertThat(gsp(1, 12)).isEqualTo(2);
    }

}
