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

import java.nio.ByteBuffer;
import java.util.function.Supplier;

/**
 * Double word (32-bit) format attribute specification.
 */
public final class DWordSpec extends FixedSizeAttributeSpec<Integer> {

	/**
	 * Constructs a new {@linkplain DWordSpec} instance.
	 *
	 * @param name the attribute's name.
	 */
	public DWordSpec(Supplier<String> name) {
		super(Integer.class, name);
	}

	/**
	 * Constructs a new {@linkplain DWordSpec} instance.
	 *
	 * @param name the attribute's name.
	 */
	public DWordSpec(String name) {
		super(Integer.class, name);
	}

	/**
	 * Convenience function for constructing a {@linkplain DWordSpec} instance with pre-configured decimal format.
	 *
	 * @param name the attribute's name.
	 * @return the created spec instance.
	 */
	public static DWordSpec dec(Supplier<String> name) {
		return dec(new DWordSpec(name));
	}

	/**
	 * Convenience function for constructing a {@linkplain DWordSpec} instance with pre-configured decimal format.
	 *
	 * @param name the attribute's name.
	 * @return the created spec instance.
	 */
	public static DWordSpec dec(String name) {
		return dec(new DWordSpec(name));
	}

	private static DWordSpec dec(DWordSpec spec) {
		spec.format(PrettyFormat.INT_FORMATTER);
		return spec;
	}

	/**
	 * Convenience function for constructing a {@linkplain DWordSpec} instance with pre-configured hexadecimal format.
	 *
	 * @param name the attribute's name.
	 * @return the created spec instance.
	 */
	public static DWordSpec hex(Supplier<String> name) {
		return hex(new DWordSpec(name));
	}

	/**
	 * Convenience function for constructing a {@linkplain DWordSpec} instance with pre-configured hexadecimal format.
	 *
	 * @param name the attribute's name.
	 * @return the created spec instance.
	 */
	public static DWordSpec hex(String name) {
		return hex(new DWordSpec(name));
	}

	private static DWordSpec hex(DWordSpec spec) {
		spec.format(HexFormat.INT_FORMATTER);
		return spec;
	}

	/**
	 * Convenience function for constructing a {@linkplain DWordSpec} instance with pre-configured decimal format and
	 * size renderer.
	 *
	 * @param name the attribute's name.
	 * @return the created spec instance.
	 */
	public static DWordSpec size(Supplier<String> name) {
		return size(new DWordSpec(name));
	}

	/**
	 * Convenience function for constructing a {@linkplain DWordSpec} instance with pre-configured decimal format and
	 * size renderer.
	 *
	 * @param name the attribute's name.
	 * @return the created spec instance.
	 */
	public static DWordSpec size(String name) {
		return size(new DWordSpec(name));
	}

	private static DWordSpec size(DWordSpec spec) {
		spec.format(PrettyFormat.INT_FORMATTER);
		spec.renderer(DWordSizeRenderer.RENDERER);
		return spec;
	}

	@Override
	protected int size() {
		return 4;
	}

	@Override
	protected Integer decodeValue(ByteBuffer buffer) {
		return buffer.getInt();
	}

}