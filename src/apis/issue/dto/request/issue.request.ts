import {Field, InputType} from "@nestjs/graphql";

@InputType()
export class IssueRequest {
  @Field(() => String)
  id: string
}