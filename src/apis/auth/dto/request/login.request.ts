import {Field, InputType} from "@nestjs/graphql";

@InputType()
export class LoginRequest {
  @Field(() => String)
  email: string

  @Field(() => String)
  password: string
}