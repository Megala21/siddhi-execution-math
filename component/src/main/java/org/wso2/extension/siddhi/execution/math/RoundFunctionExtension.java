/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.execution.math;

import org.wso2.siddhi.core.config.ExecutionPlanContext;
import org.wso2.siddhi.core.exception.ExecutionPlanRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.ExecutionPlanValidationException;

/*
* round(a);
* Returns the closest value to the argument, with ties rounding up. For example, the round value of 3.35 is
*   3.
* Accept Type(s):DOUBLE/FLOAT/
* Return Type(s): LONG/INT
*/
public class RoundFunctionExtension extends FunctionExecutor {

    private Attribute.Type returnType;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors, ExecutionPlanContext executionPlanContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new ExecutionPlanValidationException("Invalid no of arguments passed to math:round() function, " +
                    "required 1, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE) || (attributeType == Attribute.Type.FLOAT))) {
            throw new ExecutionPlanValidationException("Invalid parameter type found for the argument of math:round() function, " +
                    "required " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }

        if (attributeType == Attribute.Type.FLOAT) {
            returnType = Attribute.Type.INT;
        } else {
            returnType = Attribute.Type.LONG;
        }
    }

    @Override
    protected Object execute(Object[] data) {
        return null;  //Since the round function takes in only 1 parameter, this method does not get called. Hence, not implemented.
    }

    @Override
    protected Object execute(Object data) {
        if (data != null) {
            //type-conversion
            if (data instanceof Float) {
                float inputValue = (Float) data;
                return Math.round(inputValue);
            } else if (data instanceof Double) {
                double inputValue = (Double) data;
                return Math.round(inputValue);
            }
        } else {
            throw new ExecutionPlanRuntimeException("Input to the math:round() function cannot be null");
        }
        return null;
    }

    @Override
    public void start() {
        //Nothing to start.
    }

    @Override
    public void stop() {
        //Nothing to stop.
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

    @Override
    public Object[] currentState() {
        return null;    //No need to maintain state.
    }

    @Override
    public void restoreState(Object[] state) {
        //Since there's no need to maintain a state, nothing needs to be done here.
    }
}
