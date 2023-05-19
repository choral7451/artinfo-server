import {Field, InputType} from "@nestjs/graphql";

@InputType()
export class UserRequest {
  @Field(() => String)
  id: string
}