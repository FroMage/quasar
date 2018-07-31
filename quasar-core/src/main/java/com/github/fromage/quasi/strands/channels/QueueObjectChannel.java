/*
 * Quasar: lightweight threads and actors for the JVM.
 * Copyright (c) 2013-2014, Parallel Universe Software Co. All rights reserved.
 * 
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *  
 *   or (per the licensee's choosing)
 *  
 * under the terms of the GNU Lesser General Public License version 3.0
 * as published by the Free Software Foundation.
 */
package com.github.fromage.quasi.strands.channels;

import com.github.fromage.quasi.strands.channels.Channels.OverflowPolicy;
import com.github.fromage.quasi.strands.queues.BasicQueue;

/**
 *
 * @author pron
 */
public class QueueObjectChannel<Message> extends QueueChannel<Message> {
    public QueueObjectChannel(BasicQueue<Message> queue, OverflowPolicy policy, boolean singleProducer, boolean singleConsumer) {
        super(queue, policy, singleProducer, singleConsumer);
    }
}
