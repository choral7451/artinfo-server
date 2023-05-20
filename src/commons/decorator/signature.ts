import {createParamDecorator, ExecutionContext} from '@nestjs/common';
import {GqlExecutionContext} from "@nestjs/graphql";
import * as jwt from 'jsonwebtoken';

export const Signature = createParamDecorator((_role: string = '', context: ExecutionContext) => {
  const ctx = GqlExecutionContext.create(context);
  const authorization = ctx.getContext().req.headers.authorization;
  const token = authorization && authorization.replace(/^Bearer\s+/, '');


  return jwt.verify(token, process.env.JWT_TOKEN_KEY!);
});
