/*
 * Copyright (c) 2013, intarsys consulting GmbH
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of intarsys nor the names of its contributors may be used
 *   to endorse or promote products derived from this software without specific
 *   prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package de.intarsys.security.smartcard.pcsc;

import java.lang.reflect.Field;

import de.intarsys.security.smartcard.pcsc.nativec._PCSC_RETURN_CODES;
import de.intarsys.tools.message.MessageBundle;

public class PCSCException extends Exception implements _PCSC_RETURN_CODES {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static MessageBundle Msg = PACKAGE.Messages;

	private int errorCode = 0;

	public PCSCException(int errorCode) {
		this.errorCode = errorCode;
	}

	public PCSCException(int errorCode, Throwable cause) {
		this.errorCode = errorCode;
		initCause(cause);
	}

	public PCSCException(String message) {
		super(message);
	}

	public PCSCException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		if (errorCode == 0) {
			return super.getMessage();
		}

		Field[] fields = _PCSC_RETURN_CODES.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				if (fields[i].getInt(null) == errorCode) {
					String name = fields[i].getName();
					String msg = Msg.basicGetString("PCSCException.error."
							+ name);
					if (msg != null) {
						return msg;
					}
					return Msg.getString("PCSCException.error.default", name);
				}
			} catch (IllegalAccessException e) {
				//
			}
		}
		return Msg.getString("PCSCException.error.default", errorCode); //$NON-NLS-1$
	}
}
