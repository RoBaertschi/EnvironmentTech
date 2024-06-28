/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.unittest;

import org.junit.jupiter.api.Test;

import robaertschi.environmenttech.client.screen.ProgressArrowUtils;

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
