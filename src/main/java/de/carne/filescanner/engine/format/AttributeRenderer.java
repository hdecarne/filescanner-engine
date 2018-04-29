/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.filescanner.engine.format;

import java.io.IOException;

import de.carne.filescanner.engine.transfer.FileScannerResultOutput;

/**
 * Render function for {@linkplain AttributeSpec} elements.
 *
 * @param <T> the actual attribute value type.
 */
@FunctionalInterface
public interface AttributeRenderer<T> {

	/**
	 * Renders an attribute value.
	 *
	 * @param out the {@linkplain FileScannerResultOutput} buffer to render into.
	 * @param value the value to render.
	 * @throws IOException if an I/O error occurs.
	 * @throws InterruptedException if the render thread has been interrupted.
	 */
	void render(FileScannerResultOutput out, T value) throws IOException, InterruptedException;

}
