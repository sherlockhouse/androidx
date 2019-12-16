/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.camera.core;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.mock;

import android.os.Build;

import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.testing.fakes.FakeAppConfig;
import androidx.camera.testing.fakes.FakeCameraDeviceSurfaceManager;
import androidx.camera.testing.fakes.FakeCameraFactory;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.internal.DoNotInstrument;

import java.util.concurrent.Executor;

@SmallTest
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
@Config(minSdk = Build.VERSION_CODES.LOLLIPOP)
public class CameraXConfigTest {

    private CameraXConfig mCameraXConfig;

    @Before
    public void setUp() {
        mCameraXConfig = FakeAppConfig.create();
    }

    @Test
    public void canGetConfigTarget() {
        Class<CameraX> configTarget = mCameraXConfig.getTargetClass(/*valueIfMissing=*/ null);
        assertThat(configTarget).isEqualTo(CameraX.class);
    }

    @Test
    public void canGetCameraFactory() {
        CameraFactory cameraFactory = mCameraXConfig.getCameraFactory(/*valueIfMissing=*/ null);
        assertThat(cameraFactory).isInstanceOf(FakeCameraFactory.class);
    }

    @Test
    public void canGetDeviceSurfaceManager() {
        CameraDeviceSurfaceManager surfaceManager =
                mCameraXConfig.getDeviceSurfaceManager(/*valueIfMissing=*/ null);
        assertThat(surfaceManager).isInstanceOf(FakeCameraDeviceSurfaceManager.class);
    }

    @Test
    public void canGetCameraExecutor() {
        Executor mockExecutor = mock(Executor.class);
        CameraXConfig cameraXConfig = new CameraXConfig.Builder()
                .setCameraExecutor(mockExecutor)
                .build();
        Executor cameraExecutor = cameraXConfig.getCameraExecutor(/*valueIfMissing=*/ null);
        assertThat(cameraExecutor).isEqualTo(mockExecutor);
    }
}
