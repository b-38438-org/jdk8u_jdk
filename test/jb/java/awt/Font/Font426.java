/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;

/*
 * @test
 * @summary regression test on JRE-426 Crash after launching Rider for a few seconds
 * @run main/othervm Font426
 */

/*
 * Description: The test copies the font DFCaiDai.ttf into the folder containing System fonts. Then it get all fonts
 * and tries to get <code>getCharWidth</code> for the characters <code>" "</code>, <code>"l"</code> and <code>"W"</code>.
 *
 * Please note if the font was created via <code>Font.createFont(fontFormat, fontFile)</code> then
 * <code>getCharWidth</code> for this font does not reproduce the bug JRE-426.
 *
 * The font DFCaiDai.ttf was kindly provided by DynaComware Taiwan Inc (http://www.dynacw.com.tw) and can be used within
 * this research only.
 *
 */
public class Font426 {

    private static final int DEFAULT_SIZE = 12;
    private static final FontRenderContext DEFAULT_CONTEXT = new FontRenderContext(null, false, false);

    private static int getCharWidth(Font font, char ch) {
        if (font.canDisplay(ch)) {
            Rectangle bounds = font.getStringBounds(new char[]{ch}, 0, 1, DEFAULT_CONTEXT).getBounds();
            if (!bounds.isEmpty()) return bounds.width;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

        for (Font font : fonts) {
            if (font.getName().compareTo("DFCaiDai-Bd-HKP-BF") == 0)
                getCharWidth(font, ' ');
        }
    }
}