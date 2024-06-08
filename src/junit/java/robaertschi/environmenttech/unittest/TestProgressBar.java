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
