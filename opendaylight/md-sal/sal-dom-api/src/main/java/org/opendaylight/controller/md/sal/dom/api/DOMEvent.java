/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.md.sal.dom.api;

import java.util.Date;

/**
 * Generic event interface
 */
public interface DOMEvent {

    /**
     * Get the time of the event occurrence
     *
     * @return the event time
     */
    Date getEventTime();
}
