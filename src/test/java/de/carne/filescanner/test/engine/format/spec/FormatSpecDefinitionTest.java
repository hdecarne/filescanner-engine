/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.filescanner.test.engine.format.spec;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.carne.filescanner.engine.format.PrettyFormat;
import de.carne.filescanner.engine.format.spec.SizeRenderer;

/**
 * Test {@FormatSpecDefinition} class.
 */
class FormatSpecDefinitionTest {

	@Test
	void testFormatSpecDefinition() throws IOException {
		TestFormatSpecDefinition testFormat = new TestFormatSpecDefinition();

		testFormat.addByteAttributeFormatter("TestFormat", PrettyFormat.BYTE_FORMATTER);
		testFormat.addWordAttributeFormatter("TestFormat", PrettyFormat.SHORT_FORMATTER);
		testFormat.addDWordAttributeFormatter("TestFormat", PrettyFormat.INT_FORMATTER);
		testFormat.addQWordAttributeFormatter("TestFormat", PrettyFormat.LONG_FORMATTER);
		testFormat.addByteAttributeRenderer("TestRenderer", SizeRenderer.BYTE_RENDERER);
		testFormat.addWordAttributeRenderer("TestRenderer", SizeRenderer.SHORT_RENDERER);
		testFormat.addDWordAttributeRenderer("TestRenderer", SizeRenderer.INT_RENDERER);
		testFormat.addQWordAttributeRenderer("TestRenderer", SizeRenderer.LONG_RENDERER);
		testFormat.load();
	}

}
